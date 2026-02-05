package br.com.joaofelipe.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel,HttpServletRequest request){
      System.out.println("Chegou no controller");
      var idUser = request.getAttribute("IdUser");
      taskModel.setIdUser((UUID)idUser);

      var currentDate = LocalDateTime.now();
      if(currentDate.isAfter(taskModel.getStartData())||currentDate.isAfter(taskModel.getEndData()) ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("A data de início/fim deve ser no maior que a data atual");
      }

      if(taskModel.getStartData().isAfter(taskModel.getEndData())){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("A data de início deve ser menor que a data de fim");
      }
      
       var task = this.taskRepository.save(taskModel);  
       return ResponseEntity.status(HttpStatus.OK).body(task);
        
    }

    }

