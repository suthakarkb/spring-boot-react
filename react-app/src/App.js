import React, { Component, forwardRef } from "react";
import axios from "axios";
import MaterialTable from "material-table";
import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import logo from './logo.svg';
import './App.css';

const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

const columns = [
  { title: 'User Name', field: 'userId' },
  { title: 'Role', field: 'userRole' },
  { title: 'Name', field: 'name' },
  { title: 'Email', field: 'email' },
  { title: 'Company', field: 'company' }
];

//const pesistData = 'http://localhost:8080/';

export default class App extends Component {

constructor(props) {
     super(props);
     this.state = {  data:[]  }
}


async componentDidMount() {
    await this.retrieveAllUsers();
}

retrieveAllUsers = async () => {
  try {
      await axios.get('/users')
      .then(res => {
        console.log(res);
        console.log(res.data);
        if (res.data) { this.setState({ data:res.data }); }
      })
    } catch (err) {
      console.log(err.message);
    }
}

addUser = (user) => {
  user.departCode = '10';
  delete user.tableData;
  let headers: {
  'Access-Control-Allow-Origin' : '*',
  'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS' }
  try {
      axios.post('/user/add', user, {
        headers: headers
        }).then(res => {
        console.log(res);
      })
    } catch (err) {
      console.log(err.message);
    }
}

updateUser = (user) => {
  user.departCode = '10';
  delete user.tableData;
  let headers: {
  'Access-Control-Allow-Origin' : '*',
  'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS' }
  try {
      axios.put('/user/update', user, {
        headers: headers
      }).then(res => {
        console.log(res);
      })
    } catch (err) {
      console.log(err.message);
    }
}

deleteUser = (user) => {
  console.log('deleteUser',user);
  let headers: {
  'Access-Control-Allow-Origin' : '*',
  'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS' }
  try {
      axios.delete('/user/delete',  {data: user}, {
        headers: headers
      }
  ).then(res => {
        console.log(res);
      })
    } catch (err) {
      console.log(err.message);
    }
}

render() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" height="10%" />

        <div style={{  padding: '10px 0px 0px 10px', maxWidth: "100%" }}>
        <MaterialTable
          icons={tableIcons}
          title="User List"
          columns={columns}
          data={this.state.data}
          editable={{
              onRowAdd: newData =>
                  new Promise((resolve, reject) => {
                      setTimeout(() => {
                          let data1 = [];
                          if(this.state.data && this.state.data.length > 0) {
                            for(let i=0; i< this.state.data.length; i++)
                              data1.push(this.state.data[i]);
                          }
                          data1.push(newData);
                          //setData([...data, newData]);
                          this.setState({ data: data1 });
                          this.addUser(newData);
                          resolve();
                      }, 1000);
                  }),
              onRowUpdate: (newData, oldData) =>
                    new Promise((resolve, reject) => {
                      setTimeout(() => {
                          const dataUpdate = [...this.state.data];
                          const index = oldData.tableData.id;
                          dataUpdate[index] = newData;
                          this.updateUser(newData);
                          resolve();
                          this.setState({ data: dataUpdate });
                      }, 1000);
                  }),
              onRowDelete: oldData =>
                  new Promise((resolve, reject) => {
                      setTimeout(() => {
                          const dataDelete = [...this.state.data];
                          const index = oldData.tableData.id;
                          this.deleteUser(dataDelete[index]);
                          dataDelete.splice(index, 1);
                          this.setState({ data: dataDelete });
                          resolve();
                      }, 1000);
                  })
            }}
       />
    </div>

      </header>
    </div>
  );
 }
}
