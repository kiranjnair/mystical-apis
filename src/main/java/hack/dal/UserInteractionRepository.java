
package hack.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hack.dialog.model.UserInteraction;

public interface UserInteractionRepository extends MongoRepository<UserInteraction, String> {

	
	List<UserInteraction> findByUserid(String userid);
	
	

  

}
																									