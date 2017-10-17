
package hack.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hack.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

	List<Schedule> findBydrugname(String name);
	List<Schedule> findBytime(String time);
	List<Schedule> findByampm(String ampm);
	List<Schedule> findByfreqency(String freqency);
	List<Schedule> findByuseruid(String useruid);

}
																									