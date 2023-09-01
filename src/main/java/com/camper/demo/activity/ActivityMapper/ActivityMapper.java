import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    private final ModelMapper modelMapper;

    public ActivityMapper() {
        this.modelMapper = new ModelMapper();
    }

    public ActivityDTO toDTO(Activity activity) {
        return modelMapper.map(activity, ActivityDTO.class);
    }

    public Activity fromDTO(ActivityDTO activityDTO) {
        return modelMapper.map(activityDTO, Activity.class);
    }
}
