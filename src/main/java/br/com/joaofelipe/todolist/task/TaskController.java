package br.com.joaofelipe.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaofelipe.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;


    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody TaskModel taskModel,HttpServletRequest request){
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
      @GetMapping("/")
      public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("IdUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    //Http://localhost:8080/tasks/49034-fsdfdsfhnyjhkj-39590928
    @PutMapping("/{id}")
      public TaskModel uptade(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

        var task = this.taskRepository.findById(id).orElse(null);

        Utils.copyNonNullProperties(taskModel, task);


        return this.taskRepository.save(task);
      }

  }
