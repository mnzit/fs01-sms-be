package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.model.Subject;
import com.sudreeshya.sms.repository.SubjectRespository;
import com.sudreeshya.sms.request.SaveSubjectRequest;
import com.sudreeshya.sms.request.UpdateSubjectRequest;
import com.sudreeshya.sms.response.dto.SubjectDTO;
import com.sudreeshya.sms.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRespository subjectRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRespository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse findAllSubjects() {
        final List<Subject> subjectList = subjectRepository.findAll();
        if(subjectList.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        List<SubjectDTO> response = new ArrayList<>();
        response = subjectList
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList());
        log.info("subjects: {}", response);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse findSubjectById(Long id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        if(subjectOptional.get().getIsActive() == 'N'){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_WAS_DELETED);
        }
        else {
            SubjectDTO response = modelMapper.map(subjectOptional.get(), SubjectDTO.class);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_FOUND, response);
        }
    }

    @Override
    public GenericResponse saveSubject(SaveSubjectRequest request) {
        Subject subject = modelMapper.map(request, Subject.class);
        if(subject.getName().isEmpty() || subject.getDescription().isEmpty() || subject.getCode().isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_CANT_BE_EMPTY);
        }
        subject.setCreatedBy(new ApplicationUser(1L));
        subject.setIsActive('Y');
        subjectRepository.save(subject);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_SAVED);
    }

    @Override
    public GenericResponse updateSubject(UpdateSubjectRequest request, Long id) {
        log.info("Entered updateSubject() method!");
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        log.info("SubjectOptional: {}", subjectOptional);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }

        Subject subject = new Subject();
        subject = modelMapper.map(request, Subject.class);
        log.info("subjectResponse: {}", subject);
        if(subject.getName().isEmpty() || subject.getDescription().isEmpty() || subject.getCode().isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_CANT_BE_EMPTY);
        }
        subject.setId(id);
        subject.setIsActive('Y');
        subject.setCreatedBy(new ApplicationUser(1L));
        subjectRepository.save(subject);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_UPDATED);

    }

    @Override
    public GenericResponse deleteSubject(Long id) {

        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        else{
            Subject subject = new Subject();
            subject = modelMapper.map(subjectOptional.get(), Subject.class);
            subject.setCreatedBy(new ApplicationUser(1L));
            subject.setIsActive('N');
            subjectRepository.save(subject);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedSubjects() {
        log.info("Entered findDeletedStudents() in StudentServiceImpl!");
        final List<Subject> subjectList = subjectRepository.findAll();
        if(subjectList.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        log.info("subjectList: {}", subjectList);

        List<SubjectDTO> subjectDTOList = new ArrayList<>();

        subjectDTOList = subjectList
                .stream()
                .map(sub -> modelMapper.map(sub, SubjectDTO.class))
                .collect(Collectors.toList());

        log.info("subjectList: {}", subjectList);

        List<SubjectDTO> subjectTrash = new ArrayList<>();

        for(SubjectDTO s: subjectDTOList){
            if(s.getIsActive() == 'N'){
                subjectTrash.add(s);
            }
        }

        log.info("subjectTrash: {}", subjectTrash);

        if(subjectTrash.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        else {
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_FOUND, subjectTrash);
        }
    }
}

