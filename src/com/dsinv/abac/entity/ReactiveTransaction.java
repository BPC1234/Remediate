package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="reactive_transaction")
public class ReactiveTransaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="reactive_project_id")
    private ReactiveProject reactiveProject;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="transaction_id")
    private Transaction transaction;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReactiveProject getReactiveProject() {
        return reactiveProject;
    }

    public void setReactiveProject(ReactiveProject reactiveProject) {
        this.reactiveProject = reactiveProject;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "ReactiveTransaction{" +
                "id=" + id +
                ", reactiveProject=" + reactiveProject +
                ", transaction=" + transaction +
                '}';
    }
}
