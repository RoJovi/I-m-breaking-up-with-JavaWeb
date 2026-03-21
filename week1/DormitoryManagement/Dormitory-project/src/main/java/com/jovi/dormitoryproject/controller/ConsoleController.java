package com.jovi.dormitoryproject.controller;

import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.Repair;
import com.jovi.dormitoryproject.pojo.User;
import com.jovi.dormitoryproject.service.DormitoryService;
import com.jovi.dormitoryproject.service.RepairService;
import com.jovi.dormitoryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleController implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RepairService repairService;

    @Autowired
    private DormitoryService dormitoryService;

    private Scanner scanner = new Scanner(System.in);
    private User currentUser = null;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                if ("student".equals(currentUser.getRole())) {
                    showStudentMenu();
                } else if ("admin".equals(currentUser.getRole())) {
                    showAdminMenu();
                }
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n===========================");
        System.out.println("🏠 宿舍报修管理系统");
        System.out.println("===========================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请选择操作（输入 1-3）：");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                register();
                break;
            case "3":
                System.out.println("感谢使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("无效选择，请重新输入！");
        }
    }

    private void login() {
        System.out.println("\n===== 用户登录 =====");
        System.out.print("请输入账号：");
        String userId = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        User user = userService.login(userId, password);
        if (user != null) {
            currentUser = user;
            System.out.println(currentUser);
            System.out.println("登录成功！角色：" + ("student".equals(user.getRole()) ? "学生" : "管理员"));
        } else {
            System.out.println("登录失败！账号或密码错误。");
        }
    }

    private void register() {
        System.out.println("\n===== 用户注册 =====");
        System.out.print("请选择角色（1-学生，2-管理员）：");
        String roleChoice = scanner.nextLine();
        String role;
        if ("1".equals(roleChoice)) {
            role = "student";
        } else if ("2".equals(roleChoice)) {
            role = "admin";
        } else {
            System.out.println("无效选择！");
            return;
        }

        System.out.print("请输入账号：");
        String userId = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        System.out.print("请确认密码：");
        String confirmPassword = scanner.nextLine();

        if (userService.register(userId, password, confirmPassword, role)) {
            System.out.println("注册成功！请返回主界面登录。");
        } else {
            System.out.println("注册失败！账号可能已存在或格式不正确。");
        }
    }

    private void showStudentMenu() {
        Dormitory dorm = dormitoryService.getDormitory(currentUser.getUserId());
        if (dorm == null) {
            System.out.println("\n⚠️ 首次登录请先绑定宿舍信息！");
            bindDormitory();
        }

        while (true) {
            System.out.println("\n===== 学生菜单 =====");
            System.out.println("1. 绑定/修改宿舍");
            System.out.println("2. 创建报修单");
            System.out.println("3. 查看我的报修记录");
            System.out.println("4. 取消报修单");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");
            System.out.print("请选择操作（输入 1-6）：");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    bindDormitory();
                    break;
                case "2":
                    createRepairOrder();
                    break;
                case "3":
                    viewMyOrders();
                    break;
                case "4":
                    cancelOrder();
                    break;
                case "5":
                    changePassword();
                    break;
                case "6":
                    currentUser = null;
                    System.out.println("已退出登录！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void bindDormitory() {
        System.out.println("\n===== 绑定宿舍 =====");
        System.out.print("请输入楼栋号：");
        String building = scanner.nextLine();
        System.out.print("请输入房间号：");
        String roomNumber = scanner.nextLine();

        if (dormitoryService.bindDormitory(currentUser.getUserId(), building, roomNumber)) {
            System.out.println("宿舍绑定成功！");
        } else {
            System.out.println("宿舍绑定失败！");
        }
    }

    private void createRepairOrder() {
        Dormitory dorm = dormitoryService.getDormitory(currentUser.getUserId());
        if (dorm == null) {
            System.out.println("请先绑定宿舍！");
            return;
        }

        System.out.println("\n===== 创建报修单 =====");
        System.out.println("设备类型：");
        System.out.println("1. 空调");
        System.out.println("2. 热水器");
        System.out.println("3. 灯管");
        System.out.println("4. 水龙头");
        System.out.println("5. 其他");
        System.out.print("请选择设备类型（1-5）：");
        String typeChoice = scanner.nextLine();
        String equipmentType;
        switch (typeChoice) {
            case "1": equipmentType = "空调"; break;
            case "2": equipmentType = "热水器"; break;
            case "3": equipmentType = "灯管"; break;
            case "4": equipmentType = "水龙头"; break;
            default: equipmentType = "其他";
        }

        System.out.print("请详细描述问题：");
        String description = scanner.nextLine();

        Repair order = repairService.createOrder(
                currentUser.getUserId(),
                equipmentType,
                description,
                dorm.getBuilding(),
                dorm.getRoomNumber()
        );

        if (order != null) {
            System.out.println("报修单创建成功！单号：" + order.getOrderId());
        } else {
            System.out.println("创建失败！");
        }
    }

    private void viewMyOrders() {
        System.out.println("\n===== 我的报修记录 =====");
        List<Repair> orders = repairService.getUserOrders(currentUser.getUserId());

        if (orders.isEmpty()) {
            System.out.println("暂无报修记录。");
            return;
        }

        System.out.println("序号\t单号\t\t设备类型\t状态\t创建时间");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < orders.size(); i++) {
            Repair order = orders.get(i);
            System.out.printf("%d\t%s\t%s\t%s\t%tF %tT\n",
                    i + 1,
                    order.getOrderId(),
                    order.getEquipment(),
                    order.getStatus(),
                    order.getCreateTime(),
                    order.getCreateTime()
            );
        }

        System.out.print("\n输入序号查看详情（0返回）：");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            return;
        }
        if (index >= 0 && index < orders.size()) {
            viewOrderDetail(orders.get(index).getOrderId());
        }
    }

    private void viewOrderDetail(String orderId) {
        Repair order = repairService.getOrderDetail(orderId);
        if (order != null) {
            System.out.println("\n===== 报修单详情 =====");
            System.out.println("单号：" + order.getOrderId());
            System.out.println("设备类型：" + order.getEquipment());
            System.out.println("问题描述：" + order.getDescription());
            System.out.println("状态：" + order.getStatus());
            System.out.println("楼栋：" + order.getBuilding());
            System.out.println("房间号：" + order.getRoomNumber());
            System.out.println("创建时间：" + order.getCreateTime());
            System.out.println("最后修改：" + order.getUpdateTime());
        }
    }

    private void cancelOrder() {
        System.out.println("\n===== 取消报修单 =====");
        List<Repair> orders = repairService.getUserOrders(currentUser.getUserId());

        if (orders.isEmpty()) {
            System.out.println("暂无报修记录。");
            return;
        }

        System.out.println("可取消的报修单（仅待处理状态）：");
        int count = 0;
        for (Repair order : orders) {
            if ("待处理".equals(order.getStatus())) {
                count++;
                System.out.printf("%d. %s - %s\n", count, order.getOrderId(), order.getEquipment());
            }
        }

        if (count == 0) {
            System.out.println("没有可取消的报修单。");
            return;
        }

        System.out.print("请输入要取消的报修单号：");
        String orderId = scanner.nextLine();

        if (repairService.cancelOrder(orderId, currentUser.getUserId())) {
            System.out.println("报修单已取消！");
        } else {
            System.out.println("取消失败！只能取消待处理状态的报修单。");
        }
    }

    private void changePassword() {
        System.out.println("\n===== 修改密码 =====");
        System.out.print("请输入原密码：");
        String oldPassword = scanner.nextLine();
        System.out.print("请输入新密码：");
        String newPassword = scanner.nextLine();
        System.out.print("请确认新密码：");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("两次输入的新密码不一致！");
            return;
        }

        if (userService.changePassword(currentUser.getUserId(), oldPassword, newPassword)) {
            System.out.println("密码修改成功！请重新登录。");
            currentUser = null;
        } else {
            System.out.println("密码修改失败！原密码错误。");
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n===== 管理员菜单 =====");
            System.out.println("1. 查看所有报修单");
            System.out.println("2. 查看报修单详情");
            System.out.println("3. 更新报修单状态");
            System.out.println("4. 删除报修单");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");
            System.out.print("请选择操作（输入 1-6）：");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllOrders();
                    break;
                case "2":
                    viewOrderDetailAdmin();
                    break;
                case "3":
                    updateOrderStatus();
                    break;
                case "4":
                    deleteOrder();
                    break;
                case "5":
                    changePassword();
                    break;
                case "6":
                    currentUser = null;
                    System.out.println("已退出登录！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void viewAllOrders() {
        System.out.println("\n===== 所有报修单 =====");
        System.out.print("请选择筛选状态（1-全部，2-待处理，3-处理中，4-已完成）：");
        String filter = scanner.nextLine();

        List<Repair> orders;
        switch (filter) {
            case "2":
                orders = repairService.getOrdersByStatus("待处理");
                break;
            case "3":
                orders = repairService.getOrdersByStatus("处理中");
                break;
            case "4":
                orders = repairService.getOrdersByStatus("已完成");
                break;
            default:
                orders = repairService.getAllOrders();
        }

        if (orders.isEmpty()) {
            System.out.println("暂无报修单。");
            return;
        }

        System.out.println("序号\t单号\t\t学号\t\t设备类型\t状态");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < orders.size(); i++) {
            Repair order = orders.get(i);
            System.out.printf("%d\t%s\t%s\t%s\t%s\n",
                    i + 1,
                    order.getOrderId(),
                    order.getUserId(),
                    order.getEquipment(),
                    order.getStatus()
            );
        }
    }

    private void viewOrderDetailAdmin() {
        System.out.print("\n请输入报修单号：");
        String orderId = scanner.nextLine();
        viewOrderDetail(orderId);
    }

    private void updateOrderStatus() {
        System.out.print("\n请输入报修单号：");
        String orderId = scanner.nextLine();

        Repair order = repairService.getOrderDetail(orderId);
        if (order == null) {
            System.out.println("报修单不存在！");
            return;
        }

        System.out.println("当前状态：" + order.getStatus());
        System.out.println("可选状态：");
        System.out.println("1. 待处理");
        System.out.println("2. 处理中");
        System.out.println("3. 已完成");
        System.out.print("请选择新状态（1-3）：");
        String statusChoice = scanner.nextLine();

        String newStatus;
        switch (statusChoice) {
            case "1": newStatus = "待处理"; break;
            case "2": newStatus = "处理中"; break;
            case "3": newStatus = "已完成"; break;
            default:
                System.out.println("无效选择！");
                return;
        }

        if (repairService.updateOrderStatus(orderId, newStatus)) {
            System.out.println("状态更新成功！");
        } else {
            System.out.println("更新失败！");
        }
    }

    private void deleteOrder() {
        System.out.print("\n请输入要删除的报修单号：");
        String orderId = scanner.nextLine();

        System.out.print("确认删除吗？(y/n)：");
        String confirm = scanner.nextLine();

        if ("y".equalsIgnoreCase(confirm)) {
            if (repairService.deleteOrder(orderId)) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
        }
    }
}
