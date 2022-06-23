package com.digging.entity;

import lombok.Data;

@Data
public class Follow {
    //关注id
    private Long id;
    //博主id
    private Long hostId;
    //关注者id
    private Long guestId;
}
