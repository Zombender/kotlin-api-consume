package org.kkbp.proyectointernacionalizacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsFilter {
    private Long facultyId;
    private Long coordinatorId;
    private Date startDate;
    private Date endDate;
}
