package com.galaxy.common.id;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.common.exception.UnexpectedRuntimeException;

public class IdWorker {
	protected static final Logger logger = LoggerFactory.getLogger(IdWorker.class);

	private static final String START_WORKING = "worker starting. timestamp left shift {}, app id bits {}, worker id bits {}, sequence bits {}, node id {}";
	private static final String INVALID_NODE_ID = "node Id can't be greater than %d or less than 0";
	private static final String ABNORMAL_CLOCK_MOVE = "Clock moved backwards. Refusing to generate id for %d milliseconds";
	private static final String INVALID_APP_ID = "App Id can't be greater than %d or less than 0";

	private int nodeId;
	private int appId;
	private static long sequence = 0L;
	private static long twepoch = 1288834974657L;
	private static long nodeIdBits = 5L;
	private static long appIdBits = 5L;
	private static long maxNodeId = -1L ^ (-1L << nodeIdBits);
	private static long maxAppId = -1L ^ (-1L << appIdBits);
	private static long sequenceBits = 12L;
	private static long nodeIdShift = sequenceBits;
	private static long appIdShift = sequenceBits + nodeIdBits;
	private static long timestampLeftShift = sequenceBits + nodeIdBits + appIdBits;
	private static long sequenceMask = -1L ^ (-1L << sequenceBits);
	private static long lastTimestamp = -1L;
	private static Map<Long, IdWorker> idInstanceMap = new HashMap<Long, IdWorker>();

	public synchronized long nextId() {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new UnexpectedRuntimeException(String.format(ABNORMAL_CLOCK_MOVE, lastTimestamp - timestamp));
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;
		return ((timestamp - twepoch) << timestampLeftShift) | (appId << appIdShift) | (nodeId << nodeIdShift)
				| sequence;
	}

	public static long generateId() {
		return getDefaultInstance().nextId();
	}

	public static String generateUuid() {
		return UUID.randomUUID().toString();
	}

	public static String generateUuidWithoutDash() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static synchronized IdWorker getDefaultInstance() {
		return getInstance(0, 0);
	}

	public static synchronized IdWorker getInstance(int appId, int nodeId) {
		long key = (appId << appIdBits) + nodeId;
		IdWorker instance = null;

		if (idInstanceMap.containsKey(key)) {
			instance = (IdWorker) idInstanceMap.get(key);
		} else {
			instance = new IdWorker(appId, nodeId);
			idInstanceMap.put(key, instance);
		}
		return instance;
	}

	private IdWorker() {
		// do nothing
	}

	private IdWorker(int appId, int nodeId) {
		this.nodeId = nodeId;
		this.appId = appId;

		if (nodeId > maxNodeId || nodeId < 0) {
			throw new UnexpectedRuntimeException(String.format(INVALID_NODE_ID, maxNodeId));
		}
		if (appId > maxAppId || appId < 0) {
			throw new UnexpectedRuntimeException(String.format(INVALID_APP_ID, maxAppId));
		}
		logger.info(START_WORKING, timestampLeftShift, appIdBits, nodeIdBits, sequenceBits, nodeId);
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}
}
