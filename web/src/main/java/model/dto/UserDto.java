package model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.bean.UserBean;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto extends BaseDto<UserBean> {
    private String id;
    private String email;
    private String name;
    private Timestamp createdAt;

    @Override
    UserBean toBean() {
        return UserBean.builder()
                .id(id)
                .email(email)
                .name(name)
                .createdAt(createdAt)
                .build();
    }

    @Override
    UserDto fromBean(UserBean bean) {
        return UserDto.builder()
                .id(bean.getId())
                .email(bean.getEmail())
                .name(bean.getName())
                .createdAt(bean.getCreatedAt())
                .build();
    }

}
