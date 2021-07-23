package com.dbe.domain.vacancy;


import javax.persistence.*;

@Entity
@Table(name="vacancy_detail",schema = "recruitmentDB")
public class VacancyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vacancy_detail_sequence")
    @SequenceGenerator(name = "vacancy_detail_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "VACANCY_detail_ID_SEQ")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vacancyId",referencedColumnName = "id")
    private Vacancy vacancy;
    private String title;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
