/**
 * @Author : RiteshSingh
 * @Date : 06-July-2023
 * @FileName: TaskSub.java
 */
package com.kloc.crm.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The TaskSub entity represents a sub-task related to a main task in the CRM system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taskSubId")
    @GenericGenerator(name = "taskSubId", type = com.kloc.crm.Entity.IdGenerator.class,
            parameters = {
                    @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "000001"),
                    @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "taskSub_"),
                    @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")
            })
    @Column(name = "taskSubId")
    private String taskSubId;

    @ManyToOne
//    @JsonBackReference("taskStatus")
    private Status taskStatus; // The status of the sub-task

    private LocalDate statusDate; // Date of the sub-task status

    @ManyToOne
//    @JsonBackReference("taskOutcome")
    private Status taskOutcome; // The outcome of the sub-task

    private LocalDate followUpDate; // Follow-up date for the sub-task

    private String taskFeedback; // Feedback related to the sub-task

    private String leadFeedback; // Feedback from the lead associated with the sub-task

    private LocalDate feedbackDate; // Date of the feedback

    @ManyToOne
//    @JsonBackReference("salesActivity")
    private Status salesActivity; // The sales activity associated with the sub-task

    @ManyToOne
    @JsonBackReference("offeringId")
    private Offering offeringId; // The offering associated with the sub-task

    @ManyToOne
    @JsonBackReference("task")
    private Task task; // The main task associated with the sub-task
}
