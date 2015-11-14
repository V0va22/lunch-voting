package test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.model.Vote;
import test.model.VotePK;

import java.util.Date;

@Repository
public interface VoteRepository extends CrudRepository<Vote, VotePK> {

    Iterable<Vote> findByDate(Date date);
}
