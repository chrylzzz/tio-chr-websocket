package com.sdsoon.http.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created By Chr on 2019/4/12.
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = -8512731416624297334L;

    private Integer id;

    private String loginname;

    private String nick;

    private String ip;

}
