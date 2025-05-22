package org.kkbp.proyectointernacionalizacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private int totalReports;
    private int totalSentStudents;
    private int totalReceivedStudents;
    private int totalVirtualStudents;
    private int totalSentTeachers;
    private int totalReceivedTeachers;
    private int totalVirtualTeachers;
    private int totalNewAgreements;
    private int totalEvents;

    private Map<String, Integer> reportsByFaculty;
    private Map<String, Integer> reportsByCoordinator;

    private Map<String, Integer> sentStudentsByFaculty;
    private Map<String, Integer> receivedStudentsByFaculty;
    private Map<String, Integer> virtualStudentsByFaculty;

    private Map<String, Integer> sentTeachersByFaculty;
    private Map<String, Integer> receivedTeachersByFaculty;
    private Map<String, Integer> virtualTeachersByFaculty;
}
