package uk.ac.ebi.subs.repository.repos.status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.method.P;
import uk.ac.ebi.subs.repository.model.ProcessingStatus;
<<<<<<< HEAD
import uk.ac.ebi.subs.repository.security.PostAuthorizeProcessingStatusTeamName;
import uk.ac.ebi.subs.repository.security.PreAuthorizeProcessingStatusTeamName;
import uk.ac.ebi.subs.repository.security.PreAuthorizeSubmissionIdTeamName;
=======
import uk.ac.ebi.subs.repository.projections.ProcessingStatusWithAlias;
>>>>>>> master

import java.util.List;
import java.util.stream.Stream;


@RepositoryRestResource(excerptProjection = ProcessingStatusWithAlias.class)
public interface ProcessingStatusRepository extends MongoRepository<ProcessingStatus, String>, ProcessingStatusRepositoryCustom {

    // exported as GET /things/:id
    @Override
    @RestResource(exported = true)
    @PostAuthorizeProcessingStatusTeamName
    public ProcessingStatus findOne(String id);

    // exported as GET /things
    @Override
    @RestResource(exported = false)
    Page<ProcessingStatus> findAll(Pageable pageable);

    // Prevents POST /things and PATCH /things/:id
    @Override
    @RestResource(exported = true)
    @PreAuthorizeProcessingStatusTeamName
    <S extends ProcessingStatus> S save(@P("processingStatus") S processingStatus);

    // exported as DELETE /things/:id
    @Override
    @RestResource(exported = false)
    void delete(ProcessingStatus t);

    @RestResource(exported = false)
    List<ProcessingStatus> findBySubmissionId(String submissionId);

    @RestResource(exported = true)
<<<<<<< HEAD
    @PostAuthorizeProcessingStatusTeamName
    ProcessingStatus findBySubmittableId(@Param("submittableId") String submittableId);
=======
    ProcessingStatus findBySubmittableId(@Param("itemId") String itemId);
>>>>>>> master

    @RestResource(exported = false)
    void deleteBySubmissionId(String submissionId);

<<<<<<< HEAD
    @RestResource(exported = true)
    @PreAuthorizeSubmissionIdTeamName
=======
    @RestResource(exported = true, rel = "by-submission")
>>>>>>> master
    Page<ProcessingStatus> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);

    @RestResource(exported = true, rel = "by-submission-and-type")
    Page<ProcessingStatus> findBySubmissionIdAndSubmittableType(@Param("submissionId") String submissionId, @Param("type") String type, Pageable pageable);
}
