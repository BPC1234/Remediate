package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by amjad on 5/2/14.
 */

@Entity
@Table(name = "reactive_transaction_comment")
public class ReactiveTransactionComment extends AbstractBaseEntity {
    private String comment;

    @Column(name = "commenter_name")
    private String commenterName;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="reactive_transaction_id")
    private ReactiveTransaction reactiveTransaction;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public ReactiveTransaction getReactiveTransaction() {
        return reactiveTransaction;
    }

    public void setReactiveTransaction(ReactiveTransaction reactiveTransaction) {
        this.reactiveTransaction = reactiveTransaction;
    }

    @Override
    public String toString() {
        return "ReactiveTransactionComment{" +
                "comment='" + comment + '\'' +
                ", commenterName='" + commenterName + '\'' +
                ", commentDate=" + commentDate +
                ", reactiveTransaction=" + reactiveTransaction +
                '}';
    }
}
