package org.yujin.mallapi.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_todo")
@Getter //세터는 잘안하고 보통 change로 만듬
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    
    //tno, title, writer, complete, dueData
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    
    private String title;
    private String writer;
    private boolean complete;
    private LocalDate dueDate;
    
    // test코드에 
    // todo.changeTitle("Update Title...");
    // todo.changeComplete(true);
    // todo.changeDueDate(java.time.LocalDate.of(2024, 10, 10));
    // 위 세개의 change 메소드가 만들어졌어 이 메소드를 이 도메인에서 만들어줘
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



}
