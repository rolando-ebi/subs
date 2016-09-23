package uk.ac.ebi.subs.frontend;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.subs.FrontendApplication;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.repository.SubmissionRepository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendApplication.class)
public class ApiDocumentation {
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    SubmissionController submissionController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    /*
        TODO
        Useful example:

        https://github.com/EBISPOT/OLS/blob/master/ols-web/src/test/java/uk/ac/ebi/spot/ols/api/ApiDocumentation.java

        gives this

        http://www.ebi.ac.uk/ols/docs/api

     */

    @Before
    public void setUp() {
        this.document = document("{method-name}"
                ,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void submissions() throws Exception {
        this.submissionRepository.deleteAll();

        Submission sub = Helpers.generateTestSubmission();

        this.submissionRepository.save(sub);

        this.mockMvc.perform(get("/api/submissions/search/domain?domainName={domainName}",sub.getDomain().getName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("submission-list-example",
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("Canonical link for this resource") //TODO
                                ),
                                responseFields(
                                        fieldWithPath("_links").description("<<resources-page-links,Links>> to other resources"),
                                        fieldWithPath("_embedded.submissions").description("Submissions matching the domain name"),
                                        fieldWithPath("page.size").description("The number of resources in this page"),
                                        fieldWithPath("page.totalElements").description("The total number of resources"),
                                        fieldWithPath("page.totalPages").description("The total number of pages"),
                                        fieldWithPath("page.number").description("The page number")
                                )
                        )
                );
    }


}
