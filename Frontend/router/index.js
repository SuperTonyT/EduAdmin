import Vue from 'vue'
import Router from 'vue-router'
import Login from "../components/Login";
import Admin from "../components/Admin";
import A_Control from "../components/A_Control";
import A_Student from "../components/A_Student";
import A_Teacher from "../components/A_Teacher";
import A_Institute from "../components/A_Institute";
import A_Course from "../components/A_Course";
import A_ActiveCourse from "../components/A_ActiveCourse";
import A_ChangePassword from "../components/A_ChangePassword";
import Student from "../components/Student";
import S_Choose from "../components/S_Choose"
import S_Course from "../components/S_Course";
import S_Selected from "../components/S_Selected";
import S_Score from "../components/S_Score";
import S_ChangePassword from "../components/S_ChangePassword";
import Teacher from "../components/Teacher";
import T_Course from "../components/T_Course";
import T_ChangePassword from "../components/T_ChangePassword";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      children: [
        {
          path: '/a_control',
          name: 'A_Control',
          component: A_Control
        },
        {
          path: '/a_student',
          name: 'A_Student',
          component: A_Student
        },
        {
          path: '/a_teacher',
          name: 'A_Teacher',
          component: A_Teacher
        },
        {
          path: '/a_institute',
          name: 'A_Institute',
          component: A_Institute
        },
        {
          path: '/a_course',
          name: 'A_Course',
          component: A_Course
        },
        {
          path: '/a_activecourse',
          name: 'A_ActiveCourse',
          component: A_ActiveCourse
        },
        {
          path: '/a_changepassword',
          name: 'A_ChangePassword',
          component: A_ChangePassword
        }
      ]
    },
    {
      path: '/student',
      name: 'Student',
      component: Student,
      children: [
        {
          path: '/s_choose',
          name: 'S_Choose',
          component: S_Choose,
          children: [
            {
              path: '/s_course',
              name: 'S_Course',
              component: S_Course
            },
            {
              path: '/s_selected',
              name: 'S_Selected',
              component: S_Selected
            }
          ]
        },
        {
          path: '/s_score',
          name: 'S_Score',
          component: S_Score
        },
        {
          path: '/s_changepassword',
          name: 'S_ChangePassword',
          component: S_ChangePassword
        }
      ]
    },
    {
      path: '/teacher',
      name: 'Teacher',
      component: Teacher,
      children: [
        {
          path: '/t_course',
          name: 'T_Course',
          component: T_Course
        },
        {
          path: '/t_changepassword',
          name: 'T_ChangePassword',
          component: T_ChangePassword
        }
      ]
    }
  ]
})
