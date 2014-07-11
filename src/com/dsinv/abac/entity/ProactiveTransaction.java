package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="proactive_transaction")
public class ProactiveTransaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="proactive_project_id")
    private ProactiveProject proactiveProject;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="transaction_id")
    private Transaction transaction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProactiveProject getProactiveProject() {
        return proactiveProject;
    }

    public void setProactiveProject(ProactiveProject proactiveProject) {
        this.proactiveProject = proactiveProject;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "ProactiveTransaction{" +

                ", proactiveProject=" + proactiveProject +
                ", transaction=" + transaction +
                '}';
    }
}
