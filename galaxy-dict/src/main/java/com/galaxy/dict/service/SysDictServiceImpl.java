package com.galaxy.dict.service;

import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.dict.domain.SysDictRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;
import com.galaxy.dict.domain.entity.SysDict;
import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictRepository sysDictRepository;

    @Cacheable("sysDict")
    @Override
    public SysDict getSysDict(String sysDictFullCode) throws BusinessException {
        return sysDictRepository.findBySysDictFullCode(sysDictFullCode)
                .orElseThrow(() -> new BusinessException("DIC1002").setPlaceHolder(sysDictFullCode));
    }

    @Cacheable("sysDictName")
    @Override
    public String getSysDictName(String sysDictFullCode) throws BusinessException {
        SysDict sysDict = getSysDict(sysDictFullCode);
        return sysDict.getSysDictName();
    }

    @Cacheable("childSysDicts")
    @Override
    public List<SysDict> getChildSysDicts(String parentFullCode) {
        return sysDictRepository.findByParentFullCodeOrderBySortId(parentFullCode);
    }

    @Cacheable("bizDictCode")
    @Override
    public String getBizDictCode(String sysDictFullCode) throws BusinessException {
        SysDict sysDict = getSysDict(sysDictFullCode);
        return sysDict.getBizDictCode();
    }

    @Cacheable("sysDictByParentFullCodeAndBizDictCode")
    @Override
    public SysDict getSysDictByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode) throws BusinessException {
        return sysDictRepository.findByParentFullCodeAndBizDictCode(parentFullCode, bizDictCode)
                .orElseThrow(() -> new BusinessException("DIC1004").setPlaceHolder(parentFullCode, bizDictCode));
    }

    @Cacheable("sysDictNameByParentFullCodeAndBizDictCode")
    @Override
    public String getSysDictNameByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode) throws BusinessException {
        SysDict sysDict = getSysDictByParentFullCodeAndBizDictCode(parentFullCode, bizDictCode);
        return sysDict.getSysDictName();
    }

    @Override
    public SysDict getSysDictTree() {
        List<SysDict> flatList = sysDictRepository.findAll();
        List<SysDict> topLevelList = convertToTree(flatList);
        SysDict root = new SysDict();
        root.setSysDictCode("root");
        root.setSysDictFullCode("");
        root.setSysDictName("字典根节点");
        root.setIsLeaf(topLevelList.size() > 0);
        root.setSortId(1);
        for (SysDict topLevel : topLevelList) {
            topLevel.setParentFullCode("root");
        }
        root.setChildren(topLevelList);
        return root;
    }

    private List<SysDict> convertToTree(List<SysDict> flatList) {
        List<SysDict> treeList = flatList.stream().filter(item -> Strings.isNullOrEmpty(item.getParentFullCode()))
                .collect(Collectors.toList());
        for (SysDict root : treeList) {
            setChildren(root, flatList);
        }
        return treeList;
    }

    private void setChildren(SysDict parent, List<SysDict> flatList) {
        List<SysDict> children = flatList.stream().filter(item -> (!Strings.isNullOrEmpty(item.getParentFullCode()))
                && item.getParentFullCode().equals(parent.getSysDictFullCode())).collect(Collectors.toList());
        if (children.size() == 0) {
            parent.setIsLeaf(true);
            return;
        }
        parent.setIsLeaf(false);
        parent.setChildren(children);
        for (SysDict child : children) {
            setChildren(child, flatList);
        }
    }

    @CacheEvict(value = {"sysDict", "sysDictName", "childSysDicts", "bizDictCode",
            "sysDictByParentFullCodeAndBizDictCode", "sysDictByParentFullCodeAndBizDictCode",
            "sysDictNameByParentFullCodeAndBizDictCode"}, allEntries = true)
    @Override
    public void addSysDict(SysDict sysDict) throws BusinessException {
        ReflectionUtil.fillCommonFields(sysDict, ENTITY_SAVE_METHOD_ENUM.CREATE, "SYSTEM");
        if (Strings.isNullOrEmpty(sysDict.getParentFullCode())) {
            sysDict.setSysDictFullCode(sysDict.getSysDictCode());
        } else {
            SysDict parentDict = sysDictRepository.findBySysDictFullCode(sysDict.getParentFullCode())
                    .orElseThrow(() -> new BusinessException("DIC1002").setPlaceHolder(sysDict.getSysDictFullCode()));
            sysDict.setSysDictFullCode(parentDict.getSysDictFullCode() + "_" + sysDict.getSysDictCode());
        }
        sysDictRepository.save(sysDict);
    }

    @CacheEvict(value = {"sysDict", "sysDictName", "childSysDicts", "bizDictCode",
            "sysDictByParentFullCodeAndBizDictCode", "sysDictByParentFullCodeAndBizDictCode",
            "sysDictNameByParentFullCodeAndBizDictCode"}, allEntries = true)
    @Override
    public void editSysDict(SysDict sysDict) throws BusinessException {
        SysDict sysDictFromDb = sysDictRepository.findBySysDictFullCode(sysDict.getSysDictFullCode())
                .orElseThrow(() -> new BusinessException("DIC1002").setPlaceHolder(sysDict.getSysDictCode()));
        BeanUtils.copyProperties(sysDict, sysDictFromDb, ReflectionUtil.getCommonFields("sysDictId"));
        ReflectionUtil.fillCommonFields(sysDictFromDb, ENTITY_SAVE_METHOD_ENUM.UPDATE, "SYSTEM");
        sysDictRepository.save(sysDictFromDb);
    }

    @CacheEvict(value = {"sysDict", "sysDictName", "childSysDicts", "bizDictCode",
            "sysDictByParentFullCodeAndBizDictCode", "sysDictByParentFullCodeAndBizDictCode",
            "sysDictNameByParentFullCodeAndBizDictCode"}, allEntries = true)
    @Override
    public void deleteSysDict(String sysDictFullCode) throws BusinessException {
        int childSysDictNum = sysDictRepository.countByParentFullCode(sysDictFullCode);
        if (childSysDictNum > 0) {
            throw new BusinessException("DIC1003").setPlaceHolder(sysDictFullCode);
        }
        SysDict sysDict = sysDictRepository.findBySysDictFullCode(sysDictFullCode)
                .orElseThrow(() -> new BusinessException("DIC1002").setPlaceHolder(sysDictFullCode));
        if (sysDict != null) {
            sysDictRepository.delete(sysDict);
        }
    }
}
