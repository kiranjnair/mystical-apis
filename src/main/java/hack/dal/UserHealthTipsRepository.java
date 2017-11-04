
package hack.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hack.api.dialog.UserHealthTips;

public interface UserHealthTipsRepository extends MongoRepository<UserHealthTips, String> {


	List<UserHealthTips> findByUserid(String userid);

  

}
																									