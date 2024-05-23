package model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.bean.RequestBean;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestDto extends BaseDto<RequestBean> {
    private String id;
    private UserDto user;
    private String firstImage;
    private String secondImage;
    private Boolean result;
    private Double distance;
    private Timestamp createdAt;

    public boolean isResultNull() {
        return this.result == null;
    }

    public boolean isDistanceNull() {
        return this.distance == null;
    }

    public boolean isResult() {
        return this.result != null && this.result;
    }

    public double getDistanceValue() {
        return this.distance != null ? this.distance : 0;
    }

    @Override
    public RequestBean toBean() {
        return RequestBean.builder()
                .id(id)
                .user(user.toBean())
                .firstImage(firstImage)
                .secondImage(secondImage)
                .result(result)
                .distance(distance)
                .createdAt(createdAt)
                .build();
    }

    @Override
    public RequestDto fromBean(RequestBean bean) {
        return RequestDto.builder()
                .id(bean.getId())
                .user(new UserDto().fromBean(bean.getUser()))
                .firstImage(bean.getFirstImage())
                .secondImage(bean.getSecondImage())
                .result(bean.getResult())
                .distance(bean.getDistance())
                .createdAt(bean.getCreatedAt())
                .build();
    }

}
