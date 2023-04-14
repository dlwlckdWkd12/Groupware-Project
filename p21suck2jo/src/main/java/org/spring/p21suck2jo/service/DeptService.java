package org.spring.p21suck2jo.service;

import lombok.RequiredArgsConstructor;
import org.spring.p21suck2jo.constructor.DeptConstructors;
import org.spring.p21suck2jo.constructor.PoliceConstructors;
import org.spring.p21suck2jo.dto.DeptDto;
import org.spring.p21suck2jo.dto.PoliceDto;
import org.spring.p21suck2jo.entity.DeptEntity;
import org.spring.p21suck2jo.entity.PoliceEntity;
import org.spring.p21suck2jo.repository.DeptRepository;
import org.spring.p21suck2jo.repository.PoliceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeptService {

    private final DeptRepository deptRepository;
    private final PoliceRepository policeRepository;


    public void deptInsert(DeptDto deptDto){
      deptRepository.save(DeptConstructors.deptCreate(deptDto));
    }


    public List<DeptDto> deptList(){
        List<DeptDto> deptDtoList = new ArrayList<>();
        List<DeptEntity> list= deptRepository.findAll();

        for(DeptEntity deptEntity : list){
            deptDtoList.add(DeptConstructors.beLongToDept(deptEntity,deptEntity.getPoliceList().size()));
        }
        return deptDtoList;
    }

    public List<PoliceDto> deptDetail(Long deptId) {

        List<PoliceEntity> policeEntities = policeRepository.findAlldeptId(deptId);
        List<PoliceDto> policeDtos = new ArrayList<>();

        for (PoliceEntity policeEntity : policeEntities) {
            PoliceDto policeDto = PoliceConstructors.officerView(policeEntity);
            policeDtos.add(policeDto);
        }

        return policeDtos;
    }


    public DeptDto beLongToDept(Long id){
        DeptEntity dept=deptRepository.findByDeptId(id);
        return DeptConstructors.beLongToDept(dept,dept.getPoliceList().size());
    }

    public void deptUpdate(DeptDto deptDto){
       DeptEntity dept= DeptConstructors.deptUpdate(deptDto);
        deptRepository.save(dept);
    }

    public void depteDelete(Long id){
         DeptEntity deptEntity = deptRepository.findByDeptId(id);
         deptRepository.delete(deptEntity);
    }



}
