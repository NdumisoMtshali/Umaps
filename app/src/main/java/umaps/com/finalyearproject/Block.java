package umaps.com.finalyearproject;

public class Block {

    String block_name;
    String block_image;
    String block_uid;
    String block_coordinates;

    public Block() {
    }

    public Block(String block_name, String block_image, String block_uid, String block_coordinates) {
        this.block_name = block_name;
        this.block_image = block_image;
        this.block_uid = block_uid;
        this.block_coordinates = block_coordinates;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_image() {
        return block_image;
    }

    public void setBlock_image(String block_image) {
        this.block_image = block_image;
    }

    public String getBlock_uid() {
        return block_uid;
    }

    public void setBlock_uid(String block_uid) {
        this.block_uid = block_uid;
    }

    public String getBlock_coordinates() {
        return block_coordinates;
    }

    public void setBlock_coordinates(String block_coordinates) {
        this.block_coordinates = block_coordinates;
    }
}
