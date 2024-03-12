package com.losmessias.leherer.controller_tests;

import com.losmessias.leherer.controller.HomeworkController;
import com.losmessias.leherer.domain.*;
import com.losmessias.leherer.domain.enumeration.HomeworkStatus;
import com.losmessias.leherer.repository.CommentRepository;
import com.losmessias.leherer.repository.HomeworkRepository;
import com.losmessias.leherer.service.HomeworkService;
import com.losmessias.leherer.service.JwtService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = HomeworkController.class)
public class HomeworkControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private HomeworkService homeworkService;
    @MockBean
    private HomeworkRepository homeworkRepository;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private CommentRepository commentRepository;

    @Mock
    private Homework homeworkTest1;
    @Mock
    private Homework homeworkTest2;

    @BeforeEach
    void setUp() {
        homeworkTest1 = Homework.builder()
                .id(1L)
                .assignment(new Comment())
                .deadline(LocalDateTime.now().plusDays(1))
                .classReservation(
                        ClassReservation
                                .builder()
                                .id(1L)
                                .professor(Professor
                                        .builder()
                                        .id(1L)
                                        .build()
                                )
                                .student(Student.builder().id(1L).build())
                                .build())
                .status(HomeworkStatus.PENDING)
                .build();
        homeworkTest2 = Homework.builder()
                .id(2L)
                .assignment(new Comment())
                .deadline(LocalDateTime.now().plusDays(1))
                .classReservation(
                        ClassReservation
                                .builder()
                                .id(2L)
                                .professor(Professor
                                        .builder()
                                        .id(1L)
                                        .build()
                                )
                                .student(Student.builder().id(1L).build())
                                .build())
                .status(HomeworkStatus.PENDING)
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get all homeworks")
    void testGetAllHomeworks() throws Exception {
        List<Homework> homeworkList = new ArrayList<>();
        homeworkList.add(homeworkTest1);
        homeworkList.add(homeworkTest2);

        when(homeworkService.getAllHomeworks()).thenReturn(homeworkList);

        mockMvc.perform(get("/api/homework/all"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get all homeworks without authentication")
    void testGetAllHomeworksWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/homework/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get all homeworks finds none")
    void testGetAllHomeworksFindsNone() throws Exception {
        when(homeworkService.getAllHomeworks()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/homework/all"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("No homeworks found"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get homework by id")
    void testGetHomeworkById() throws Exception {
        when(homeworkService.getHomeworkById(1L)).thenReturn(homeworkTest1);

        mockMvc.perform(get("/api/homework/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get homework by id finds none")
    void testGetHomeworkByIdFindsNone() throws Exception {
        when(homeworkService.getHomeworkById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/homework/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("No homework found with id 1"));
                });
    }

    @Test
    @DisplayName("Get homework by id without authentication")
    void testGetHomeworkByIdWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/homework/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get homework by id with invalid id")
    void testGetHomeworkByIdWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/homework/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Id must be positive"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework")
    void testCreateHomework() throws Exception {
        JSONObject jsonContent = new JSONObject();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        System.out.println(deadline);
        jsonContent.put("assignment", "Assignment");
        jsonContent.put("deadline", deadline);
        jsonContent.put("classReservationId", 1L);
        jsonContent.put("professorId", 1L);
        jsonContent.put("files", null);

        when(homeworkService.createHomework(
                deadline,
                "Assignment",
                1L,
                1L,
                null
        )).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework with invalid deadline")
    void testCreateHomeworkWithInvalidDeadline() throws Exception {
        JSONObject jsonContent = new JSONObject();
        LocalDateTime deadline = LocalDateTime.now().minusDays(1);
        jsonContent.put("assignment", "Assignment");
        jsonContent.put("deadline", deadline);
        jsonContent.put("classReservationId", 1L);
        jsonContent.put("professorId", 1L);
        jsonContent.put("files", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Deadline must be in the future"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework with null assignment")
    void testCreateHomeworkWithNullAssignment() throws Exception {
        JSONObject jsonContent = new JSONObject();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        jsonContent.put("assignment", null);
        jsonContent.put("deadline", deadline);
        jsonContent.put("classReservationId", 1L);
        jsonContent.put("professorId", 1L);
        jsonContent.put("files", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Assignment must not be null"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework with null class reservation")
    void testCreateHomeworkWithNullClassReservation() throws Exception {
        JSONObject jsonContent = new JSONObject();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        jsonContent.put("assignment", "Assignment");
        jsonContent.put("deadline", deadline);
        jsonContent.put("classReservationId", null);
        jsonContent.put("professorId", 1L);
        jsonContent.put("files", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Class reservation must not be null"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework with null professor id")
    void testCreateHomeworkWithNullProfessorId() throws Exception {
        JSONObject jsonContent = new JSONObject();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        jsonContent.put("assignment", "Assignment");
        jsonContent.put("deadline", deadline);
        jsonContent.put("classReservationId", 1L);
        jsonContent.put("professorId", null);
        jsonContent.put("files", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Professor id must not be null"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create homework with null deadline")
    void testCreateHomeworkWithNullDeadline() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("assignment", "Assignment");
        jsonContent.put("deadline", null);
        jsonContent.put("classReservationId", 1L);
        jsonContent.put("professorId", 1L);
        jsonContent.put("files", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/homework/create")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().equals("Deadline must not be null"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework with valid data")
    void testRespondHomeworkWithValidData() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", "Response");
        jsonContent.put("associatedId", 5L);
        jsonContent.put("files", null);

        when(homeworkService.verifyIfResponded(any())).thenReturn(false);
        when(homeworkService.getHomeworkById(any())).thenReturn(homeworkTest1);
        when(homeworkRepository.save(any())).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("\"status\":\"DONE\","));
                    assert (result.getResponse().getContentAsString().contains("\"response\":\"Response\","));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework with invalid data")
    void testRespondHomeworkWithInvalidData() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", null);
        jsonContent.put("associatedId", 5L);
        jsonContent.put("files", null);

        when(homeworkService.verifyIfResponded(any())).thenReturn(false);
        when(homeworkService.getHomeworkById(any())).thenReturn(homeworkTest1);
        when(homeworkRepository.save(any())).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("Response must not be null"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework with invalid id")
    void testRespondHomeworkWithInvalidId() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", "Response");
        jsonContent.put("associatedId", 5L);
        jsonContent.put("files", null);

        when(homeworkService.verifyIfResponded(any())).thenReturn(false);
        when(homeworkService.getHomeworkById(any())).thenReturn(null);
        when(homeworkRepository.save(any())).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("No homework found with id 1"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework with invalid associated id")
    void testRespondHomeworkWithInvalidAssociatedId() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", "Response");
        jsonContent.put("associatedId", 1L);
        jsonContent.put("files", null);

        when(homeworkService.verifyIfResponded(any())).thenReturn(false);
        when(homeworkService.getHomeworkById(any())).thenReturn(homeworkTest1);
        when(homeworkRepository.save(any())).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("Student id does not match"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework already responded")
    void testRespondHomeworkAlreadyResponded() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", "Response");
        jsonContent.put("associatedId", 5L);
        jsonContent.put("files", null);

        when(homeworkService.verifyIfResponded(any())).thenReturn(true);
        when(homeworkService.getHomeworkById(any())).thenReturn(homeworkTest1);
        when(homeworkRepository.save(any())).thenReturn(homeworkTest1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("Homework already responded"));
                });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Give a response to homework not found")
    void testRespondHomeworkNotFound() throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("response", "Response");
        jsonContent.put("associatedId", 5L);
        jsonContent.put("files", null);

        when(homeworkService.getHomeworkById(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/homework/respond/1")
                        .contentType("application/json")
                        .content(jsonContent.toString())
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assert (result.getResponse().getContentAsString().contains("No homework found with id 1"));
                });
    }
}