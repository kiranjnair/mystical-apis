
package hack.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hack.model.Drug;

public interface DrugRepository extends MongoRepository<Drug, String> {

	List<Drug> findBydrugname(String name);

}
