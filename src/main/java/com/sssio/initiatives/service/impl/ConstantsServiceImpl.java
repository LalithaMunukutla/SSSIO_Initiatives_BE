package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.*;
import com.sssio.initiatives.response.Constants;
import com.sssio.initiatives.service.ConstantsService;
import com.sssio.initiatives.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstantsServiceImpl implements ConstantsService {

    @Autowired
    private ConstantsDAO constantsDAO;

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private ZoneDAO zoneDAO;

    @Autowired
    private QuestionTypeDAO questionTypeDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private QuestionsDAO questionsDAO;

    @Override
    public Constants getConstants() {
        Constants constants = new Constants();
        constants.setActivities(activityDAO.getAllActivities());
        constants.setCountries(countryDAO.getCountries());
        constants.setRegions(regionDAO.getRegions());
        constants.setZones(zoneDAO.getZones());
        constants.setQuestionTypes(questionTypeDAO.getAllQuestionTypes());
        constants.setRoles(roleDAO.getAllRoles());
        constants.setQuestions(questionsDAO.fetchQuestions(null));
        return constants;
    }
}
