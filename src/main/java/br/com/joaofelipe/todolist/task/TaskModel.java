package br.com.joaofelipe.todolist.task;

import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data; 


@Data
@Entity(name ="tb_tasks")
public class TaskModel {
    
    @Id 
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String descriçao;

    @Column(length = 50)
    private String titulo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataTermino;
    private String prioridade;

    private UUID idUsuario;

    @CreationTimestamp
    private LocalDateTime createdAt;

    


}
