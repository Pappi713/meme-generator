package com.example.meme.dto;

import com.example.meme.model.Reaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemeResponseDTO {
  private Long mID;
  private String name;
  private String url;
  @JsonIgnore
  private List<Reaction> reactions;
  @JsonProperty("tags")
  public Map<String, Integer> getReactionMap(){
    Map<String, Integer> result = new HashMap<>();
    for(Reaction reaction : reactions){
      if(result.containsKey(reaction.getType())){
        result.put(reaction.getType(), result.get(reaction.getType()) + 1);
        continue;
      }
      result.put(reaction.getType(), 1);
    }
    return result;
  }

}

