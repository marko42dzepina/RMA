package dzepina.plovila.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "plovila")
public class Plovilo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String naziv;
    private int tip;
    private String godinaGradnje;
    private String opis;
    private String slika;

}
