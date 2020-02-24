import dao.AccountDao;
import dao.ChargeDao;
import dao.RequestDao;
import dao.impl.AccountCrudDaoImpl;
import dao.impl.ChargeCrudDaoImpl;
import dao.impl.OperationCrudDaoImpl;
import dao.impl.RequestCrudDaoImpl;
import dao.util.ConnectorDB;
import dao.util.pages.Page;
import dao.util.pages.Pageable;

public class Main {
    public static void main(String[] args) {
        ChargeDao accountCrudDao = new ChargeCrudDaoImpl(new ConnectorDB("sqlconnection"));
        System.out.println( accountCrudDao.findById(1));
        System.out.println( accountCrudDao.findAllByAccountId(1, new Page(0,4)));
    }
}
