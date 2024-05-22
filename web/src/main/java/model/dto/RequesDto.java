package model.dto;

import java.io.File;
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
public class RequesDto extends BaseDto<RequestBean> {
    private String id;
    private UserDto user;
    private File firstImage;
    private File secondImage;
    private boolean result;
    private Timestamp createdAt;

    @Override
    RequestBean toBean() {
        return RequestBean.builder()
                .id(id)
                .user(user.toBean())
                .firstImage(firstImage)
                .secondImage(secondImage)
                .result(result)
                .createdAt(createdAt)
                .build();
    }

    @Override
    RequesDto fromBean(RequestBean bean) {
        return RequesDto.builder()
                .id(bean.getId())
                .user(new UserDto().fromBean(bean.getUser()))
                .firstImage(bean.getFirstImage())
                .secondImage(bean.getSecondImage())
                .result(bean.isResult())
                .createdAt(bean.getCreatedAt())
                .build();
    }

}
