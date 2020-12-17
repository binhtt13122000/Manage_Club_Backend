package binhtt.dev.manage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
public class GroupController {
    //get groups
    @GetMapping("/groups")
    public ResponseEntity getGroups(){
        return null;
    }

    //create groups
    @PostMapping("/groups")
    public ResponseEntity createGroup(){
        return null;
    }

    //update group
    @PutMapping("/groups/{groupId}")
    public ResponseEntity updateGroup(@PathVariable("groupId") String groupId){
        return null;
    }

    //find group by id
    @GetMapping("/groups/{groupId}")
    public ResponseEntity findOneGroup(@PathVariable("groupId") String groupId){
        return null;
    }

    //add member to group
    @PostMapping("/groups/{groupId}/users")
    public ResponseEntity addToGroup(@PathVariable("groupId") String groupId){
        return null;
    }

    //get member is/ is not in group
    @GetMapping("/groups/{groupId}/users")
    public ResponseEntity getMemberInGroup(@PathVariable("groupId") String groupId, @RequestParam("isExisted") Optional<Boolean> isExisted){
        return null;
    }

    @PutMapping("/groups/{groupId}/users/{studentId}")
    public ResponseEntity deleteMemberFromGroup(@PathVariable("groupId") String groupId, @PathVariable("studentId") String studentId){
        return null;
    }

}
