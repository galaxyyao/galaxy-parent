package com.galaxy.authentication.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Org;

@Repository
public interface OrgRepository extends JpaRepository<Org, String>{
	Optional<Org> findByOrgFullCode(String orgFullCode);
	
	List<Org> findByParentFullCode(String parentFullCode);
	
	int countByParentFullCode(String parentFullCode);
	
	List<Org> findByOrgFullCodeStartingWith(String orgFullCode);
	
	List<Org> findByLeaderUserCode(String leaderUserCode);
	
	List<Org> findByLeaderUserCodeAndOrgType(String leaderUerCode, String orgType);

	List<Org> findByOrgType(String orgType);

	List<Org> findByOrgTypeAndParentFullCode(String orgType, String parentFullCode);
}
