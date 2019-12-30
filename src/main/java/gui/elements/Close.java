package gui.elements;

/**
 * This interface is being used in the saveBtn. There an class that implements
 * this interface is required to be passed for the constructor. As the save
 * button will also close the required stage that is associated with that save
 * button
 * 
 * @author Mathieu
 *
 */
public interface Close {
	void close();
}
