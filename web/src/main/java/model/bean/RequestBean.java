package model.bean;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.uuid.Generators;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "requests")
public class RequestBean extends BaseBean {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserBean user;

    @Column(name = "first_image")
    private String firstImage;

    @Column(name = "second_image")
    private String secondImage;

    @Column(name = "result")
    private Boolean result;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public boolean isResult() {
        return this.result != null && this.result;
    }

    @Override
    public void generateId() {
        this.id = Generators.randomBasedGenerator().generate().toString();
    }

    @Override
    public void prePersistNewData() {
        generateId();
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
