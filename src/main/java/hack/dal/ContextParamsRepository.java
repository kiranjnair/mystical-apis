
package hack.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hack.model.converse.ContextParameters;

public interface ContextParamsRepository extends MongoRepository<ContextParameters, String> {

	List<ContextParameters> findByMedicine(String name);
	List<ContextParameters> findByUserid(String userid);

  

}
																									