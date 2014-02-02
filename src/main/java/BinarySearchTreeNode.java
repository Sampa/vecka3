import sun.text.resources.FormatData_in;

import java.util.Iterator;
import java.util.Map;

/**
 * Denna klass representerar noderna i ett bin�rt s�ktr�d utan dubletter.
 *
 * Detta �r den enda av de tre klasserna ni ska g�ra n�gra �ndringar i. (Om ni
 * inte vill l�gga till fler testfall.) De �ndringar som �r till�tna �r dock
 * begr�nsade av f�ljande regler:
 * <ul>
 * <li>Ni f�r INTE l�gga till n�gra fler instansvariabler.
 * <li>Ni f�r INTE l�gga till n�gra statiska variabler.
 * <li>Ni f�r INTE anv�nda n�gra loopar n�gonstans.
 * <li>Ni F�R l�gga till fler metoder, dessa ska d� vara privata.
 * </ul>
 *
 * @author henrikbe
 *
 * @param <T>
 */
public class BinarySearchTreeNode<T extends Comparable<T>> {
    private T data;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;
    public BinarySearchTreeNode(T data) {
        this.data = data;
    }
    /**
     * L�gger till en nod i det bin�ra s�ktr�det. Om noden redan existerar s�
     * l�mnas tr�det of�r�ndrat.
     *
     * @param data
     *            datat f�r noden som ska l�ggas till.
     * @return true om en ny nod lades till tr�det.
     */
    public boolean add(T data) {
        return add(data,this)!=null;
    }
    /*
    *  compareTo docs:
    *  "It is strongly recommended, but not strictly required that (x.compareTo(y)==0) == (x.equals(y))"
    *  Därför ingen if(data.equals(branchRoot)) return null;
    *  @param branchRoot noden vi är på alias subträdets root
    *  @return nya noden eller null om noden redan fanns
    */
    private BinarySearchTreeNode<T> add(T data, BinarySearchTreeNode<T> branchRoot){
        //basecase, vi har hittat en plats där nya noden ska ligga
        if(branchRoot == null)
            return new BinarySearchTreeNode(data);
        //kan ha 3 lägen: lägre==-1, högre==1, equal==0
        int result = data.compareTo(branchRoot.data);
        if(result==-1)
           branchRoot.left = add(data,branchRoot.left);
        else if(result ==1)
           branchRoot.right = add(data, branchRoot.right);
        //enda fallet vi kan komma hit är ifall result ==0 dvs, noden finns redan
        return null;
    }

    /**
     * Privat hj�lpmetod som �r till nytta vid borttag. Ni beh�ver inte
     * skriva/utnyttja denna metod om ni inte vill.
     *
     * @return det minsta elementet i det (sub)tr�d som noden utg�r root i.
     */
    private T findMin() {
        return null;
    }

    /**
     * Tar bort ett element ur tr�det. Om elementet inte existerar s l�mnas
     * tr�det of�r�ndrat.
     *
     * @param data
     *            elementet som ska tas bort ur tr�det.
     * @return en referens till nodens subtr�d efter borttaget.
     */
    public BinarySearchTreeNode<T> remove(T data) {
        return null;
    }

    /**
     * Kontrollerar om ett givet element finns i det (sub)tr�d som noden utg�r
     * root i.
     *
     * @param data
     *            det s�kta elementet.
     * @return true om det s�kta elementet finns i det (sub)tr�d som noden utg�r
     *         root i.
     */
    public boolean contains(T data) {
        return contains(data, this);
    }

    private boolean contains(T data, BinarySearchTreeNode<T> branchRoot) {
        //basecase, vi kan inte gå längre och har inte hittat noden
        if(branchRoot == null)
            return false;
        //kan ha 3 lägen: lägre==-1, högre==1, equal==0
        int result = data.compareTo(branchRoot.data);
        if(result==-1)
            branchRoot.left = add(data,branchRoot.left);
        else if(result ==1)
            branchRoot.right = add(data, branchRoot.right);
        //enda fallet vi kan komma hit är ifall result ==0 dvs noden finns
        return true;
    }

    /**
     * Storleken p� det (sub)tr�d som noden utg�r root i.
     *
     * @return det totala antalet noder i det (sub)tr�d som noden utg�r root i.
     */
    public int size() {
        return 0;
    }
    /**
     * Det h�gsta djupet i det (sub)tr�d som noden utg�r root i.
     *
     * @return djupet.
     */
    public int depth() {
        return -1;
    }

    /**
     * Returnerar en str�ngrepresentation f�r det (sub)tr�d som noden utg�r root
     * i. Denna representation best�r av elementens dataobjekt i sorterad
     * ordning med ", " mellan elementen.
     *
     * @return str�ngrepresentationen f�r det (sub)tr�d som noden utg�r root i.
     */
    public String toString() {
        return "";
    }
}
