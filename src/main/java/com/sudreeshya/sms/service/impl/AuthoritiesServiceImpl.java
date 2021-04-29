package com.sudreeshya.sms.service.impl;

import com.sudreeshya.sms.builder.ResponseBuilder;
import com.sudreeshya.sms.constant.ResponseMsgConstant;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.model.Authority;
import com.sudreeshya.sms.repository.AuthorityRepository;
import com.sudreeshya.sms.response.dto.AuthorityDTO;
import com.sudreeshya.sms.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthoritiesServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthoritiesServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse getAllAuthorities() {
        final List<Authority> authorities = authorityRepository.findAll();
        if(authorities.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.AUTHORITIES_NOT_FOUND);
        }

        List<AuthorityDTO> authorityDTOArrayList = authorities
                .stream()
                .map(authority -> modelMapper.map(authority, AuthorityDTO.class))
                .collect(Collectors.toList());

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.AUTHORITIES_FOUND, authorityDTOArrayList);
    }
}
