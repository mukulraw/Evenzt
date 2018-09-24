package loginPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 11/3/2016.
 */

public class Avatar {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("num")
    @Expose
    private Integer num;
    @SerializedName("each")
    @Expose
    private List<Each> each = new ArrayList<Each>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The num
     */
    public Integer getNum() {
        return num;
    }

    /**
     *
     * @param num
     * The num
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     *
     * @return
     * The each
     */
    public List<Each> getEach() {
        return each;
    }

    /**
     *
     * @param each
     * The each
     */
    public void setEach(List<Each> each) {
        this.each = each;
    }

}
