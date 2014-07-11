package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by amjad on 4/10/14.
 */

@Entity
@Table(name="training_question_answer")
public class TrainingQuestionAnswer extends AbstractBaseEntity{

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "is_correct")
    private boolean correct;

    @Column(name="option_order")
    private int optionOrder;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="training_question_id")
    private TrainingQuestion trainingQuestion;

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public TrainingQuestion getTrainingQuestion() {
        return trainingQuestion;
    }

    public void setTrainingQuestion(TrainingQuestion trainingQuestion) {
        this.trainingQuestion = trainingQuestion;
    }

    public int getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(int optionOrder) {
        this.optionOrder = optionOrder;
    }

    @Override
    public String toString() {
        return "TrainingQuestionAnswer{" +
                "optionText='" + optionText + '\'' +
                ", correct=" + correct +
                ", optionOrder=" + optionOrder +
                ", trainingQuestion=" + trainingQuestion +
                '}';
    }
}
