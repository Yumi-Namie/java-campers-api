package com.camper.demo.camper.dto;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.signup.entity.Signup;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CamperMapper extends ModelMapper {
    public CamperMapper() {


        Converter<List<Signup>, List<ActivityDTO>> convertSignups = new AbstractConverter<>() {
            protected List<ActivityDTO> convert(List<Signup> signups) {
                List<ActivityDTO> activityDTOs = new ArrayList<>();

                for (Signup signup : signups) {
                    Activity activity = signup.getActivity();
                    ActivityDTO activityDTO = new ActivityDTO();
                    activityDTO.setId(activity.getId());
                    activityDTO.setName(activity.getName());
                    activityDTO.setDifficulty(activity.getDifficulty());
                    activityDTOs.add(activityDTO);
                }

                return activityDTOs;
            }
        };

        createTypeMap(Camper.class, CamperWithActivitiesDTO.class)
                .addMappings(mapper -> mapper.using(convertSignups)
                        .map(Camper::getSignups, CamperWithActivitiesDTO::setActivities));
    }


}

