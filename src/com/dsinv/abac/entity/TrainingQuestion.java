package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by amjad on 4/3/14.
 */
@Entity
@Table(name="training_question")
public class TrainingQuestion extends AbstractBaseEntity {

    private String text;

    @Column(name = "question_order")
    private int questionOrder;

    @Column(name = "correct_option")
    private long correctOption;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="training_id")
    private Training training;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="trainingQuestion", cascade = CascadeType.ALL)
    @OrderBy("optionOrder")
    private Set<TrainingQuestionAnswer> trainingQuestionAnswers = new HashSet<TrainingQuestionAnswer>();

    @Transient
    private int selectedOption;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Set<TrainingQuestionAnswer> getTrainingQuestionAnswers() {
        return trainingQuestionAnswers;
    }

    public void setTrainingQuestionAnswers(Set<TrainingQuestionAnswer> trainingQuestionAnswers) {
        this.trainingQuestionAnswers = trainingQuestionAnswers;
    }

    public long getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(long correctOption) {
        this.correctOption = correctOption;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    @Override
    public String toString() {
        return "TrainingQuestion{" +
                "text='" + text + '\'' +
                ", questionOrder=" + questionOrder +
                ", training=" + training +
                ", trainingQuestionAnswers=" + trainingQuestionAnswers +
                '}';
    }
}
