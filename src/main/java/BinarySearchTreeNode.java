/*
    Daniel van den Berg(dava4507@dsv.su.se)
    Eleni Kiriakou( elki8004@dsv.su.se )
*/
public class BinarySearchTreeNode<T extends Comparable<T>> {
    private T data;
    private BinarySearchTreeNode<T> parent;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;
    private BinarySearchTreeNode<T> nextSmaller;
    private BinarySearchTreeNode<T> nextLarger;
    private int size = 1;

    public BinarySearchTreeNode(T data) {
        this.data = data;
    }
    public BinarySearchTreeNode<T> getAncestor(){
        BinarySearchTreeNode<T> start = this;
        BinarySearchTreeNode<T> asc;
        while(start !=null){
            if( this.getData().compareTo(start.getData())< 0){
                asc = start;
                start = start.getLeft();
            }else
                start = start.getRight();
        }
        return start;
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
            BinarySearchTreeNode<T> node = add(data,this);
            if(node.right !=null)
                node.nextLarger = (BinarySearchTreeNode<T>) findMin(node.right);
            else{
                node.nextLarger = getAncestor();
            }
            node.nextSmaller = (BinarySearchTreeNode<T>) findMin(node.left);

        }catch (IllegalStateException ise){
            return false;
        }
        size++;
        return true;
    }

    public T getData() {
        return data;
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
        if(branchRoot == null) {
            BinarySearchTreeNode<T> node = new BinarySearchTreeNode<T>(data);
            return node;
        }
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

    public void setSize(int size) {
        this.size = size;
    }

    private BinarySearchTreeNode<T> remove(T data, BinarySearchTreeNode<T> branchRoot) {
        if(branchRoot==null)
            return null;
        int result = data.compareTo(branchRoot.data);
        if(result<0)
            branchRoot.left = remove(data,branchRoot.left);
        else if(result>0)
            branchRoot.right = remove(data,branchRoot.right);
        else{ // found it
            size--;
            if(branchRoot.isLeaf())
                return  null;
            else if(branchRoot.hasTwoChildren()){
                branchRoot.data = findMin(branchRoot.right);
                branchRoot.right = remove(branchRoot.data, branchRoot.right);
                return branchRoot;
            }else //har bara ett barn som då, returnera det så länkas det omo
                return branchRoot.left != null ? branchRoot.left : branchRoot.right;
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
        return size;
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

    public BinarySearchTreeNode<T> getRight() {
        return right;
    }

    public BinarySearchTreeNode<T> getLeft() {
        return left;
    }
}
