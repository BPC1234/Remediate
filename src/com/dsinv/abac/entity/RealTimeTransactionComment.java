package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by amjad on 4/25/14.
 */

@Entity
@Table(name = "real_time_transaction_comment")
public class RealTimeTransactionComment extends AbstractBaseEntity {

    private String comment;

    @Column(name = "commenter_name")
    private String commenterName;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="real_time_transaction_id")
    private RealTimeTransaction realTimeTransaction;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
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
}
