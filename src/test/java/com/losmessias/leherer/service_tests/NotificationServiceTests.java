package com.losmessias.leherer.service_tests;

import com.losmessias.leherer.domain.*;
import com.losmessias.leherer.domain.enumeration.AppUserRole;
import com.losmessias.leherer.repository.NotificationProfessorRepository;
import com.losmessias.leherer.repository.NotificationStudentRepository;
import com.losmessias.leherer.service.EmailService;
import com.losmessias.leherer.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTests {

    @Mock
    private NotificationProfessorRepository notificationProfessorRepository;
    @Mock
    private NotificationStudentRepository notificationStudentRepository;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("Test set notification of professor to opened")
    void testSetProfessorNotificationToOpened() {
        NotificationProfessor notificationProfessors = new NotificationProfessor(new Professor(), "Te han cancelado la clase");
        when(notificationProfessorRepository.findById(any())).thenReturn(Optional.of(notificationProfessors));
        notificationProfessors.setOpened(true);
        assertEquals(notificationProfessors, notificationService.setProfessorNotificationToOpened(1L));

    }
    @Test
    @DisplayName("Test set notification of student to opened")
    void testSetStudentNotificationToOpened() {
        NotificationStudent notificationStudent = new NotificationStudent(new Student(), "Te han cancelado la clase");
        when(notificationStudentRepository.findById(any())).thenReturn(Optional.of(notificationStudent));
        notificationStudent.setOpened(true);
        assertEquals(notificationStudent, notificationService.setStudentNotificationToOpened(1L));
    }

    @Test
    @DisplayName("Test the generation of notifications when a class is reserved")
    void testGenerateClassReservedNotification() {
        Professor professor = new Professor();
        Subject subject = new Subject();
        Student student = new Student();
        ClassReservation classReservation = new ClassReservation(
                professor,
                subject,
                student,
                LocalDate.of(2023, 1, 1),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                0.0,
                100);
        assertEquals("Notifications sent successfully", notificationService.generateClassReservedNotification(classReservation));
    }
    @Test
    @DisplayName("Test the notification was sent to the professor when the student cancels the class")
    void testCancelClassReservedNotificationByStudent() {
        Professor professor = new Professor();
        Subject subject = new Subject();
        Student student = new Student();
        ClassReservation classReservation = new ClassReservation(
                professor,
                subject,
                student,
                LocalDate.of(2023, 1, 1),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                0.0,
                100);
        assertEquals("Notification sent successfully", notificationService.cancelClassReservedNotification(classReservation, AppUserRole.STUDENT));
    }

    @Test
    @DisplayName("Test the notification was sent to the student when the professor cancels the class")
    void testCancelClassReservedNotificationByProfessor() {
        Professor professor = new Professor();
        Subject subject = new Subject();
        Student student = new Student();
        ClassReservation classReservation = new ClassReservation(
                professor,
                subject,
                student,
                LocalDate.of(2023, 1, 1),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                0.0,
                100);
        assertEquals("Notification sent successfully", notificationService.cancelClassReservedNotification(classReservation, AppUserRole.PROFESSOR));
    }

    @Test
    @DisplayName("test the notification was sent when admin approves a lecture")
    void testLecturedApprovedByAdminNotification(){
        List<ProfessorSubject> approvedSubjects = new ArrayList<>();
        approvedSubjects.add(new ProfessorSubject(new Professor(), new Subject()));
        assertEquals( "Notification sent successfully", notificationService.lecturedApprovedByAdminNotification(approvedSubjects));
    }

    @Test
    @DisplayName("test the notification was sent when admin rejects a lecture")
    void testLecturedRejectedByAdminNotification(){
        List<ProfessorSubject> approvedSubjects = new ArrayList<>();
        approvedSubjects.add(new ProfessorSubject(new Professor(), new Subject()));
        assertEquals( "Notification sent successfully", notificationService.lecturedApprovedByAdminNotification(approvedSubjects));
    }


}
