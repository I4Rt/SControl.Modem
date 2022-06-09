package com.i4rt.demo.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MDKRPKT {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private boolean isCelebrating = false;

    private String register1RowData;
    private String register2RowData;
    private String register3RowData;
    private String register4RowData;
    private String register5RowData;
    private String register6RowData;
    private String register7RowData;
    private String register8RowData;
    private String register9RowData;
    private String register10RowData;
    private String register11RowData;
    private String register12RowData;

    private String register13RowData; //Заполняется повтором регистров 6, 7
    private String register14RowData;
    private String register15RowData; //Заполняется повтором регистров 8, 9
    private String register16RowData;

    private String register17RowData;
    private String register18RowData;
    private String register19RowData;
    private String register20RowData;


    private String log;

}
