package com.workflo.workflo_backend.user.services.impl;

import com.workflo.workflo_backend.user.models.Profile;
import com.workflo.workflo_backend.user.repository.ProfileRepository;
import com.workflo.workflo_backend.user.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class WorkfloProfileService implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public List<Profile> searchProfile(String jobTitleOrSkill) {
        return profileRepository.findProfilesByJobTitleOrSkillsIn(jobTitleOrSkill);
    }
}
