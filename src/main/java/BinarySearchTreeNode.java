//﻿import sun.text.resources.FormatData_in;
//import java.util.Iterator;
//import java.util.Map;
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

/*
    Daniel van den Berg(dava4507@dsv.su.se)
    Eleni Kiriakou( elki8004@dsv.su.se )
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
        try {
            add(data,this);
        }catch (IllegalStateException ise){
            return false;
        }
        return true;
    }
    /*
    *  compareTo docs:
    *  "It is strongly recommended, but not strictly required that (x.compareTo(y)==0) == (x.equals(y))"
    *  Därför ingen if(data.equals(branchRoot)) return null;
    *  @param branchRoot noden vi är på alias subträdets root
    *  @return nya noden
    */
    private BinarySearchTreeNode<T> add(T data, BinarySearchTreeNode<T> branchRoot){
        //basecase, vi har hittat en plats där nya noden ska ligga
        if(branchRoot == null)
            return new BinarySearchTreeNode<T>(data);
        //kan ha 3 lägen: lägre==-1, högre==1, equal==0
        int result = data.compareTo(branchRoot.data);
        if(result<0)
            branchRoot.left = add(data, branchRoot.left);
        else if(result>0)
            branchRoot.right = add(data, branchRoot.right);
        else
            throw new IllegalStateException();
        return branchRoot;
    }

    /**
     * Privat hj�lpmetod som �r till nytta vid borttag. Ni beh�ver inte
     * skriva/utnyttja denna metod om ni inte vill.
     *
     * @return det minsta elementet i det (sub)tr�d som noden utg�r root i.
     */
    private T findMin() {
        return findMin(this);
    }
    /*
    *@param branchRoot
    *       nod/subträd
    *@return T element, det lägsta i subträdet. Null om parametern branchRoot var felaktig
    */
    private T findMin(BinarySearchTreeNode<T> branchRoot) {
        if (branchRoot.left==null)
            return branchRoot.data;
        else
            return findMin(branchRoot.left);
    }

    /**
     * Tar bort ett element ur tr�det. Om elementet inte existerar s l�mnas
     * tr�det of�r�ndrat.
     *
     * @param info
     *            elementet som ska tas bort ur tr�det.
     * @return en referens till nodens subtr�d efter borttaget.
     */
    public BinarySearchTreeNode<T> remove(T info) {
        return remove(info,this);
    }
    private BinarySearchTreeNode<T> remove(T data, BinarySearchTreeNode<T> branchRoot) {
        if(branchRoot==null)
            return null;
        int result = data.compareTo(branchRoot.data);
        //3 lägen, -1== mindre,0==egual,1==större
        if(result<0)
            branchRoot.left = remove(data,branchRoot.left);
        else if(result>0)
            branchRoot.right = remove(data,branchRoot.right);
        else{
            //match
            //elementet är ett löv, returnerar vi null då så tas referensen till det bort
            if(branchRoot.isLeaf())
                return  null;
            else if(branchRoot.hasTwoChildren()){
                branchRoot.data = findMin(branchRoot.right);
                branchRoot.right = remove(branchRoot.data, branchRoot.right);
                return branchRoot;
            }else{ //har bara ett barn som då, returnera det så länkas det omo
                return branchRoot.left != null ? branchRoot.left : branchRoot.right;
            }
        }
        return branchRoot;
    }
    private boolean hasTwoChildren() {
        return this.left !=null && this.right !=null;
    }

    private boolean isLeaf() {
        return this.left==null && this.right==null;
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
        if(result<0)
            return contains(data,branchRoot.left);
        else if(result>0)
            return contains(data, branchRoot.right);
        return true;
    }

    /**
     * Storleken p� det (sub)tr�d som noden utg�r root i.
     *
     * @return det totala antalet noder i det (sub)tr�d som noden utg�r root i.
     */
    public int size() {
        return countSize(0,this);
    }
    /*
    * @param count
    *       vad räkningen är på
    * @param branchRoot
    *       noden som ska tittas på
    */
    private int countSize(int count, BinarySearchTreeNode<T> branchRoot){
        //fanns inget så räkna inget
        if(branchRoot==null)
            return 0;
        count++;
        //traversera vänstra subträdet om det finns
        count = branchRoot.left == null ? count : countSize(count,branchRoot.left);
        //travesera högra subträdet om det finns
        count = branchRoot.right == null ? count : countSize(count, branchRoot.right);
        return count;
    }
    /**
     * Det h�gsta djupet i det (sub)tr�d som noden utg�r root i.
     *
     * @return djupet.
     */
    public int depth() {
        return depth(0,this);
    }
    /*
    * @param count
    *       räknar djupet
    * @param branchRoot
    *       noden vi undersökar, kan vara null referens
    * vi räknar fram 1 och adderar det största av subträdens höjd, om vi stöter på en null-pekare tar vi bort 1
    * lite annorlunda än bokens då vi inte gör något recursivt anrop ifall vi vet att det kommer att vara tomt åt det hållet
    */
    private int depth(int count,BinarySearchTreeNode<T> branchRoot){
        //saknar branchRoot ett subträd kompenseras det med -1 så totala höjden blir 0
        //finns det ett subträd räknar vi ut höjden på det istället
        int leftDepth = branchRoot.left == null ? -1: depth(count,branchRoot.left);
        int rightDepth = branchRoot.right == null ? -1: depth(count,branchRoot.right);
        //+1 för nästa nod och den djupaste av träden
        return 1+Math.max(leftDepth,rightDepth);
    }

    /**
     * Returnerar en str�ngrepresentation f�r det (sub)tr�d som noden utg�r root
     * i. Denna representation best�r av elementens dataobjekt i sorterad
     * ordning med ", " mellan elementen.
     *
     * @return str�ngrepresentationen f�r det (sub)tr�d som noden utg�r root i.
     */
    public String toString() {
        //bygg en sträng,obs kommer ha avslutande komma och space (, )
        String string = buildString("",this);
        /* finns lite olika sätt som man kan få bort slutet på
            men det kommer ju alltid vara (samma) 2 tecken och det här borde vara effektivare
            än att smeta ned buildString med en if sats och jämförelse mot findMax()
            substring returnerar en sträng från index 0 till index length-2
         */
        return string.substring(0,string.length()-2);
    }

    /*
    * inorder traversal för att skriva ut elementen i ordning
    * Tar INTE hänsyn till vilket element i ordningen man är på
    * @param string
    *              strängen så långt
    * @param branchRoot
    *              subträdet/noden som ska skrivas ut
    */
    private String buildString(String string, BinarySearchTreeNode<T> branchRoot) {
        //vänster subträd
        if(branchRoot.left != null)
            string = buildString(string,branchRoot.left);
        //noden själv
        string +=branchRoot.data.toString()+", ";
        //höger subträd
        if(branchRoot.right != null)
            string = buildString(string,branchRoot.right);
        return string;
    }
}
