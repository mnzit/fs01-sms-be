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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRespository subjectRespository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRespository subjectRespository, ModelMapper modelMapper) {
        this.subjectRespository = subjectRespository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse findAllSubjects() {
        final List<Subject> subjects = subjectRespository.findAll();
        if(subjects.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        List<SubjectDTO> subjectDTOList = new ArrayList<>();

        subjectDTOList = subjects
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_FOUND, subjectDTOList);

    }

    @Override
    public GenericResponse findSubjectById(Long id) {
        final Optional<Subject> subjectOptional = subjectRespository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        SubjectDTO response = modelMapper.map(subjectOptional.get(), SubjectDTO.class);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_FOUND, response);

    }

    @Override
    public GenericResponse saveSubject(SaveSubjectRequest request) {
        Subject subject = modelMapper.map(request, Subject.class);
        subject.setCreatedBy(new ApplicationUser(1L));
        subjectRespository.save(subject);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_SAVED);
    }

    @Override
    public GenericResponse updateSubject(UpdateSubjectRequest request, Long id) {
        Optional<Subject> subjectOptional = subjectRespository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.SUBJECT_NOT_FOUND);
        }
        Subject subject = new Subject();
        subject = modelMapper.map(request, Subject.class);
        subject.setId(id);
        subject.setCreatedBy(new ApplicationUser(1L));
        subjectRespository.save(subject);
        return ResponseBuilder.buildSuccess(ResponseMsgConstant.SUBJECT_UPDATED);
    }
}
