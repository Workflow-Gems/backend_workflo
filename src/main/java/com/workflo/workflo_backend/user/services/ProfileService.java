package com.workflo.workflo_backend.user.services;

import com.workflo.workflo_backend.user.models.Profile;

import java.util.List;

public interface ProfileService {

    List<Profile> searchProfile(String jobTitleOrSkill);
}
