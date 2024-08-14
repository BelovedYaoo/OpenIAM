package top.prefersmin.openiam.permission.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true,fluent = true)
public class TestEntity {

    private String name;

    private int age;

}
